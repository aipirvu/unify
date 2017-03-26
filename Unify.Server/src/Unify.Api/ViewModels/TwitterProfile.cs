using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Unify.Common.Interfaces;

namespace Unify.Api.ViewModels
{
    public class TwitterProfile : ITwitterProfile
    {
        public long Id { get; set; }
        public string Name { get; set; }
        public string ScreenName { get; set; }
        public string Location { get; set; }
        public string Language { get; set; }
        public string ProfileBackgroundColor { get; set; }
        public string ProfileBackgroundImageUrl { get; set; }
        public string ProfileImageUrl { get; set; }
    }
}
