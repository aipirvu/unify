using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Unify.Data;
using Unify.Common.Interfaces;
using Unify.Common.Entities;
using MongoDB.Driver;
using Unify.Api.CustomCoverters;
using MongoDB.Bson.Serialization;
using Unify.Api.ViewModels;

namespace Unify.Api
{
    public class Startup
    {
        public Startup(IHostingEnvironment env)
        {
            var builder = new ConfigurationBuilder()
                .SetBasePath(env.ContentRootPath)
                .AddJsonFile("appsettings.json", optional: true, reloadOnChange: true)
                .AddJsonFile($"appsettings.{env.EnvironmentName}.json", optional: true);

            if (env.IsEnvironment("Development"))
            {
                // This will push telemetry data through Application Insights pipeline faster, allowing you to view results immediately.
                builder.AddApplicationInsightsSettings(developerMode: true);
            }

            builder.AddEnvironmentVariables();
            Configuration = builder.Build();
        }

        public IConfigurationRoot Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container
        public void ConfigureServices(IServiceCollection services)
        {
            // Add framework services.
            services.AddApplicationInsightsTelemetry(Configuration);

            services.AddMvc()
                    .AddJsonOptions(options =>
                    {
                        //https://brettedotnet.wordpress.com/2014/07/16/web-api-and-interface-parameters/
                        options.SerializerSettings.Converters.Add(new UserCustomConverter());
                        options.SerializerSettings.Converters.Add(new RegisterCustomConverter());
                        options.SerializerSettings.Converters.Add(new FacebookProfileCustomConverter());
                        options.SerializerSettings.Converters.Add(new LinkedInProfileCustomConverter());
                        options.SerializerSettings.Converters.Add(new FacebookLoginCustomConverter());
                        options.SerializerSettings.Converters.Add(new AppLoginCustomConverter());
                    });

            //app
            services.AddTransient<IUserAccount, UserAccount>();
            services.AddTransient<IRepository<IUserAccount>, UserRepository>();
            services.AddTransient<IUserRepository, UserRepository>();
            services.AddTransient<IMongoClient, MongoClient>();

            ConfigureMongoDBClassMap();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline
        public void Configure(IApplicationBuilder app, IHostingEnvironment env, ILoggerFactory loggerFactory)
        {
            loggerFactory.AddConsole(Configuration.GetSection("Logging"));
            loggerFactory.AddDebug();

            app.UseApplicationInsightsRequestTelemetry();

            app.UseApplicationInsightsExceptionTelemetry();

            app.UseMvc();
        }
        
        public void ConfigureMongoDBClassMap()
        {
            BsonClassMap.RegisterClassMap<UserAccount>();
            BsonClassMap.RegisterClassMap<FacebookProfile>();
            BsonClassMap.RegisterClassMap<LinkedInProfile>();
        }

    }
}
