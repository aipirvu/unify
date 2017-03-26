using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Unify.Common.Interfaces
{
    public interface ITwitterProfile
    {
        long Id { get; set; }
        string Name { get; set; }
        string ScreenName { get; set; }
        string Location { get; set; }
        string Language { get; set; }
        string ProfileBackgroundColor { get; set; }
        string ProfileBackgroundImageUrl { get; set; }
        string ProfileImageUrl { get; set; }
    }
}
