using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Unify.Common.Interfaces
{
    public interface IUnifyProfile
    {
        string Id { get; set; }
        string Username { get; set; }
        string DisplayName { get; set; }
        string Email { get; set; }
    }
}
