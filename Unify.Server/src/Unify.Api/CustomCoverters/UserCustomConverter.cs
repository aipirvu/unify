using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Common.Entities;
using Unify.Common.Interfaces;

namespace Unify.Api.CustomCoverters
{
    public class UserCustomConverter : UnifyCustomConverter<IUserAccount>
    {
        public override IUserAccount Create(Type objectType)
        {
            return new UserAccount();
        }
    }
}
