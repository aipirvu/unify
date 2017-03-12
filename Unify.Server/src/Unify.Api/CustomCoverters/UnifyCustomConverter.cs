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
    public abstract class UnifyCustomConverter<T> : CustomCreationConverter<T>
    {
        public override object ReadJson(JsonReader reader, Type objectType, object existingValue, JsonSerializer serializer)
        {
            if (reader.TokenType == JsonToken.Null)
            {
                return null;
            }

            T obj = Create(objectType);
            serializer.Populate(reader, obj);
            return obj;
        }
    }
}
