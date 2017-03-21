using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Unify.Common.Entities;
using Unify.Data;
using Unify.Common.Interfaces;

namespace Unify.Api.Controllers
{
    public abstract class UnifyController<Entity> : Controller
        where Entity : class, IUnifyEntity
    {
        protected IRepository<Entity> _repository;

        public UnifyController(IRepository<Entity> repository)
        {
            _repository = repository;
        }

        [HttpGet("{id}")]
        public virtual Entity Get(string id)
        {
            return _repository.Get(id);
        }

        [HttpGet]
        public virtual IEnumerable<Entity> Get()
        {
            return _repository.Get();
        }

        [HttpPost]
        public virtual void Post([FromBody]Entity user)
        {
            _repository.Create(user);
        }

        [HttpPut]
        public virtual void Put([FromBody]Entity user)
        {
            _repository.Update(user);
        }

        [HttpDelete("{id}")]
        public virtual void Delete(string id)
        {
            _repository.Delete(id);
        }
    }
}
