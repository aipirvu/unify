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
        private IRepository<Entity> _repository;

        public UnifyController(IRepository<Entity> repository)
        {
            _repository = repository;
        }

        [HttpGet("{idHash}")]
        public Entity Get(string idHash)
        {
            return _repository.Get(idHash);
        }

        [HttpGet]
        public IEnumerable<Entity> Get()
        {
            return _repository.Get();
        }

        [HttpPost]
        public void Post([FromBody]Entity user)
        {
            _repository.Create(user);
        }

        [HttpPut]
        public void Put([FromBody]Entity user)
        {
            /* todo fix ObjectId serialization issues:
             * http://stackoverflow.com/questions/7695730/mongodb-c-id-serialization-best-pattern/7982411#7982411
             * */
            _repository.Update(user);
        }

        [HttpDelete("{idHash}")]
        public void Delete(string idHash)
        {
            _repository.Delete(idHash);
        }

        //TEST
        [HttpGet("/echo/{echo}")]
        public string Echo(string echo)
        {
            return echo;
        }
    }
}
