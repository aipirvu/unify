using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Unify.Common.Entities;
using Unify.Data;
using Unify.Common.Interfaces;
using Unify.Api.ViewModels;
using System.Net.Http;

namespace Unify.Api.Controllers
{
    [Route("api/[controller]")]
    public class LoginController : Controller
    {
        private IUserRepository _userRepository;

        public LoginController(IUserRepository repository)
        {
            _userRepository = repository;
        }
        
        [HttpPost]
        public IActionResult Post([FromBody]Login login)
        {
            var user = _userRepository.GetByEmail(login.Email);
            if (user.Password != login.Password)
            {
                return Forbid();
            }
            return Ok();
        }
    }
}
