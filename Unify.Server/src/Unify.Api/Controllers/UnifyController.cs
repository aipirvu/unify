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
    {
        private IRepository<Entity> _repository;

        public UnifyController(IRepository<Entity> repository)
        {
            _repository = repository;
        }

        [HttpGet("{id}")]
        public Entity Get(string id)
        {
            return _repository.Get(id);
        }

        [HttpGet]
        public IEnumerable<Entity> Get()
        {
            return _repository.Get();
        }

        [HttpPost]
        public void Post([FromBody] Entity user)
        {
            _repository.Create(user);
        }

        [HttpPut]
        public void Put([FromBody]Entity user)
        {
            _repository.Update(user);
        }

        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }

        //TEST
        [HttpGet("/echo/{echo}")]
        public string Echo(string echo)
        {
            return echo;
        }
    }
}
