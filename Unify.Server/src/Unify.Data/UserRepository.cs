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
    public class UserRepository : Repository<IUser>
    {
        public UserRepository(IMongoClient client) : base(client, "users")
        { }
    }
}
