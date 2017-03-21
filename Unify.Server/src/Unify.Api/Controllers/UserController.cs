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
    public class UserController : UnifyController<IUserAccount>
    {
        public UserController(IRepository<IUserAccount> userRepository) : base(userRepository)
        { }

        [HttpPut]
        override public void Put([FromBody] IUserAccount userAccount)
        {
            IUserAccount oldUserAccount = base._repository.Get(userAccount.Id);
            userAccount.Password = oldUserAccount.Password;
            userAccount.Salt = oldUserAccount.Salt;
            base.Put(userAccount);
        }
    }
}
