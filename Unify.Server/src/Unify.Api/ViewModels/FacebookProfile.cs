using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Common.Interfaces;

namespace Unify.Api.ViewModels
{
    public class FacebookProfile : IFacebookProfile
    {
        public string Id { get; set; }
        public string Name { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public int AgeRange { get; set; }
        public string ProfileLink { get; set; }
        public string Gender { get; set; }
        public string Locale { get; set; }
        public string PictureLink { get; set; }
        public int Timezone { get; set; }
        public string Email { get; set; }
    }
}
