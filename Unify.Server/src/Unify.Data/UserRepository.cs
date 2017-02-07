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
    public class UserRepository : Repository<IUser>, IUserRepository
    {
        public UserRepository(IMongoClient client) : base(client, "users")
        { }

        public IUser GetByEmail(string email)
        {
            var filter = Builders<BsonDocument>.Filter.Eq("Email", email);
            var bsonDocument = _collection.Find(filter).FirstOrDefault();
            if (null != bsonDocument)
            {
                return (IUser)BsonSerializer.Deserialize<IUser>(bsonDocument);
            }
            return null;
        }
    }
}
