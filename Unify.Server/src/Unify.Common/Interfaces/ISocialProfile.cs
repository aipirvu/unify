using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Unify.Common.Interfaces
{
    public interface ISocialProfile : IUnifyEntity
    {
        string UserId { get; set; }
        string DisplayName { get; set; }
        string Email { get; set; }
        string ProfileId { get; set; }
        List<string> ConnectionsIds { get; set; }
    }
}
