using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Common.Interfaces;

namespace Unify.Common.Entities
{
    public class User : IUser
    {
        //public ObjectId Id { get; set; }
        //public string IdHash
        //{
        //    get
        //    {
        //        if (null != Id)
        //        {
        //            return Id.ToString();
        //        }
        //        return null;
        //    }
        //}
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }
        public string Username { get; set; }
    }
}
