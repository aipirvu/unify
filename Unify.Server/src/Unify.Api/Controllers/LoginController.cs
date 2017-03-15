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

        [HttpPost("app")]
        public IActionResult AppLogin([FromBody]IAppLogin login)
        {
            var user = _userRepository.GetByEmail(login.Email);
            if (null == user)
            {
                return NotFound();
            }
            if (user.Password != login.Password)
            {
                return Forbid();
            }
            return Ok();
        }

        [HttpPost("facebook")]
        public IActionResult FacebookLogin([FromBody]IFacebookLogin login)
        {
            var user = _userRepository.GetByEmail(login.FacebookId);
            if (null == user)
            {
                return NotFound();
            }
            return Ok(user);
        }

        [HttpPost]
        public IActionResult test()
        {
            return Ok();
        }
    }
}
