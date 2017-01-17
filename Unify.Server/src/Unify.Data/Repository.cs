﻿using MongoDB.Bson;
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
    public class Repository<Entity> : IRepository<Entity>
        where Entity : class, IUnifyEntity
    {
        protected static IMongoClient _client;
        protected static IMongoDatabase _database;
        protected static IMongoCollection<BsonDocument> _collection;
        private static MethodInfo _bsonDeserializer;

        public Repository(IMongoClient client, string collection)
        {
            _client = client;
            _database = _client.GetDatabase("unify");
            _collection = _database.GetCollection<BsonDocument>(collection);

            var concreteEntityType = GetConcreteType();
            _bsonDeserializer = typeof(BsonSerializer).GetMethod("Deserialize", new Type[] { typeof(BsonDocument), typeof(Action<BsonDeserializationContext.Builder>) })
                                                      .MakeGenericMethod(concreteEntityType);

        }

        public Entity Get(string id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Entity> Get()
        {
            var filter = new BsonDocument();
            var bsonDocuments = _collection.Find(filter);
            foreach (var bsonDocument in bsonDocuments.ToList())
            {
                yield return (Entity)_bsonDeserializer.Invoke(this, new object[] { bsonDocument, null });
            }
        }

        public void Create(Entity entity)
        {
            _collection.InsertOne(entity.ToBsonDocument());
        }

        public void Update(Entity entity)
        {
            throw new NotImplementedException();
        }

        public void Delete(string id)
        {
            throw new NotImplementedException();
        }

        private Type GetConcreteType()
        {
            /* The concrete types should be determined from assemblies but this is not available in the current version of .NET Core
             * Must be changed in the expected version 1.1
             * 
             * Also the Entity itself must be verified if it is a concrete type and use that as first option
             * */
            Type[] concreteTypes = new Type[]
            {
                typeof(User)
            };

            foreach (var concreteType in concreteTypes)
            {
                if (Activator.CreateInstance(concreteType) is Entity) { return concreteType; }
            }

            throw new Exception("A concrete type could not be determined");
        }

    }
}
