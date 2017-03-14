using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Unify.Common.Interfaces
{
    public interface IFacebookProfile
    {
        string Id { get; set; }
        string Name { get; set; }
        string FirstName { get; set; }
        string LastName { get; set; }
        int AgeRange { get; set; }
        string ProfileLink { get; set; }
        string Gender { get; set; }
        string Locale { get; set; }
        string PictureLink { get; set; }
        int Timezone { get; set; }
        string Email { get; set; }
    }
}
