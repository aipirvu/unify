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
    [Route("api/[controller]")]
    public class UserController : UnifyController<IUser>
    {
        public UserController(IRepository<IUser> userRepository) : base(userRepository)
        { }
    }
}
