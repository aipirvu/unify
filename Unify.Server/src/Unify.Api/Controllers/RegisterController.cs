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
using System.Net;

namespace Unify.Api.Controllers
{
    [Route("api/[controller]")]
    public class RegisterController : Controller
    {
        private IUserRepository _userRepository;

        public RegisterController(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        [HttpPost]
        public IActionResult Post([FromBody]IRegister register)
        {
            IUserAccount userAccount = _userRepository.GetByEmail(register.UserAccount.Email);
            if (null != userAccount)
            {
                return StatusCode((int)HttpStatusCode.Conflict);
            }

            userAccount = register.UserAccount;
            userAccount.Password = register.Password;
            _userRepository.Create(userAccount);
            return Ok();
        }
    }
}
