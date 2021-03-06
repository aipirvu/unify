﻿using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Unify.Common.Interfaces
{
    public interface IUserAccount : IUnifyEntity
    {
        string DisplayName { get; set; }
        string Password { get; set; }
        string Salt { get; set; }
        string Email { get; set; }
        IFacebookProfile FacebookProfile { get; set; }
        ILinkedInProfile LinkedInProfile { get; set; }
        ITwitterProfile TwitterProfile { get; set; }
    }
}
