using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Common.Interfaces;

namespace Unify.Api.ViewModels
{
    public class AppLogin: IAppLogin
    {
        public string Email { get; set; }
        public string Password { get; set; }
    }
}
