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
    public class RegisterController : Controller
    {
        private IUserRepository _userRepository;

        public RegisterController()
        { }

        [HttpPost]
        public IActionResult Post([FromBody]IRegister register)
        {
            return NotFound();
        }
    }
}
