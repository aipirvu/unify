using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Common.Interfaces;

namespace Unify.Common.Entities
{
    public class User : IUser
    {
        public ObjectId Id { get; set; }
        public string IdHash
        {
            get
            {
                if (null != Id)
                {
                    return Id.ToString();
                }
                return null;
            }
        }
        public string Username { get; set; }
    }
}
