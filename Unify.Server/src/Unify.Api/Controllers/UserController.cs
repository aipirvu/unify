using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Unify.Common.Entities;
using Unify.Data;

namespace Unify.Api.Controllers
{
    [Route("api/[controller]")]
    public class UserController : Controller
    {
        private IUserRepository _userRepository;

        public UserController(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        [HttpGet("{id}")]
        public User Get(string id)
        {
            return _userRepository.GetUser(id);
        }

        [HttpGet]
        public IEnumerable<User> Get()
        {
            return _userRepository.GetUsers();
        }

        [HttpPost]
        public void Post([FromBody] User user)
        {
            _userRepository.CreateUser(user);
        }

        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string username)
        {
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
