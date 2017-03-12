using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Common.Interfaces;

namespace Unify.Api.ViewModels
{
    public class SocialProfile : IFacebookProfile, IGoogleProfile, ILinkedInProfile
    {
        public List<string> ConnectionsIds { get; set; }

        public string Email { get; set; }

        public string Id { get; set; }

        public string DisplayName { get; set; }

        public string ProfileId { get; set; }
    }
}
