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
    public class ConnectController : Controller
    {
        private IUserRepository _userRepository;

        public ConnectController()
        { }

        [HttpPost]
        [Route("/facebook")]
        public IActionResult ConnectWithFacebookProfile([FromBody]string email, [FromBody]IFacebookProfile profile)
        {
            return NotFound();
        }

        [HttpPost]
        [Route("/google")]
        public IActionResult ConnectWithGoogleProfile([FromBody]string email, [FromBody]IGoogleProfile profile)
        {
            return NotFound();
        }

        [HttpPost]
        [Route("/linkedin")]
        public IActionResult ConnectWithLinkedinProfile([FromBody]string email, [FromBody]ILinkedInProfile profile)
        {
            return NotFound();
        }
    }
}
