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
    public class UserCustomConverter : CustomCreationConverter<IUser>
    {
        public override IUser Create(Type objectType)
        {
            return new User();
        }

        public override object ReadJson(JsonReader reader, Type objectType, object existingValue, JsonSerializer serializer)
        {
            if (reader.TokenType == JsonToken.Null)
            {
                return null;
            }

            IUser obj = Create(objectType);
            serializer.Populate(reader, obj);
            return obj;
        }
    }
}
