using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Common.Interfaces;

namespace Unify.Api.ViewModels
{
    public class FacebookLogin: IFacebookLogin
    {
        public string FacebookId { get; set; }
    }
}
