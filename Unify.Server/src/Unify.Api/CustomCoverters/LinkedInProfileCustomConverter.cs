using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Api.ViewModels;
using Unify.Common.Entities;
using Unify.Common.Interfaces;

namespace Unify.Api.CustomCoverters
{
    public class LinkedInProfileCustomConverter : UnifyCustomConverter<ILinkedInProfile>
    {
        public override ILinkedInProfile Create(Type objectType)
        {
            return new SocialProfile();
        }
    }
}
