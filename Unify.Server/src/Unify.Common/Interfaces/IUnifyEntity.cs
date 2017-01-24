using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Unify.Common.Interfaces
{
    public interface IUnifyEntity
    {
        //ObjectId Id { get; set; }
        //string IdHash { get; }
        string Id { get; set; }
    }
}
