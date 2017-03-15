using MongoDB.Bson;
using MongoDB.Bson.Serialization;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Common.Entities;
using System.Reflection;
using Unify.Common.Interfaces;
using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace Unify.Data
{
    public class UserRepository : Repository<IUserAccount>, IUserRepository
    {
        public UserRepository(IMongoClient client) : base(client, "userAccounts")
        { }

        public IUserAccount GetByEmail(string email)
        {
            var filter = Builders<BsonDocument>.Filter.Eq("Email", email);
            var bsonDocument = _collection.Find(filter).FirstOrDefault();
            if (null != bsonDocument)
            {
                return (IUserAccount)BsonSerializer.Deserialize<IUserAccount>(bsonDocument);
            }
            return null;
        }

        public IUserAccount GetByFacebookId(string facebookId)
        {
            var filter = Builders<BsonDocument>.Filter.Eq("facebookProfile.id", facebookId);
            var bsonDocument = _collection.Find(filter).FirstOrDefault();
            if (null != bsonDocument)
            {
                return (IUserAccount)BsonSerializer.Deserialize<IUserAccount>(bsonDocument);
            }
            return null;
        }
    }
}
