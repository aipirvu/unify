using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Unify.Common.Interfaces
{
    public interface ILinkedInProfile
    {
        string Id { get; set; }
        string FirstName { get; set; }
        string LastName { get; set; }
        string Headline { get; set; }
        string ProfileLink { get; set; }
    }
}
