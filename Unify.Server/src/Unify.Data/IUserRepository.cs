using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Common.Entities;

namespace Unify.Data
{
    public interface IUserRepository
    {
        User GetUser(string id);
        IEnumerable<User> GetUsers();
        void CreateUser(User user);
        void DeleteUser(string id);
        
    }
}
