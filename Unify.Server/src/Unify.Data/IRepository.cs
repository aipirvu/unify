using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Common.Entities;

namespace Unify.Data
{
    public interface IRepository<Entity>
    {
        Entity Get(string id);
        IEnumerable<Entity> Get();
        void Create(Entity entity);
        void Update(Entity entity);
        void Delete(string id);
        
    }
}
