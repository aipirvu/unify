using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Unify.Common.Interfaces
{
    public interface IRegister
    {
        string DisplayName { get; set; }
        string Email { get; set; }
        string Password { get; set; }
    }
}
