using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Api.ViewModels;
using Unify.Common.Interfaces;

namespace Unify.Api.CustomCoverters
{
    public class FacebookProfileCustomConverter : UnifyCustomConverter<IFacebookProfile>
    {
        public override IFacebookProfile Create(Type objectType)
        {
            return new FacebookProfile();
        }
    }
}
