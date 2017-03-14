using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Common.Entities;
using Unify.Common.Interfaces;

namespace Unify.Data
{
    public interface IUserRepository : IRepository<IUserAccount>
    {
        IUserAccount GetByEmail(string email);
    }
}
