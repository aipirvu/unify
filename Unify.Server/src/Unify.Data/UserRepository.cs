using MongoDB.Bson;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Common.Entities;

namespace Unify.Data
{
    public class UserRepository : IUserRepository
    {
        protected static IMongoClient _client;
        protected static IMongoDatabase _database;

        public UserRepository()
        {
            _client = new MongoClient();
            _database = _client.GetDatabase("unify");
        }


        public User GetUser(string id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<User> GetUsers()
        {
            //var collection = _database.GetCollection<BsonDocument>("restaurants");
            //var filter = new BsonDocument();
            //var count = 0;
            //using (var cursor = await collection.FindAsync(filter))
            //{
            //    while (await cursor.MoveNextAsync())
            //    {
            //        var batch = cursor.Current;
            //        foreach (var document in batch)
            //        {
            //            // process document
            //            count++;
            //        }
            //    }
            //}

            throw new NotImplementedException();
        }

        public void CreateUser(User user)
        {
            var document = new BsonDocument
            {
                {"username", user.Username }
            };

            var collection = _database.GetCollection<BsonDocument>("user");
            collection.InsertOne(document);
        }

        public void DeleteUser(string id)
        {
            throw new NotImplementedException();
        }
    }
}
