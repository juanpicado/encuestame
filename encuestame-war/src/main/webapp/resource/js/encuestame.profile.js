dependencies ={
    layers:  [
        {
            name: "encuestame-commons.js",
            //copyrightFile: "../../encuestame-war/apache.txt",
            dependencies: [
                "encuestame.org.core.commons.tweetPoll.TweetPoll",
                "encuestame.org.core.comments.Comments",
                "encuestame.org.core.shared.utils.AccountPicture",
                "encuestame.org.core.commons.social.LinksPublished",
                "encuestame.org.core.commons.tweetPoll.detail.TweetPollAnswer",
                "encuestame.org.core.commons.signup.Signup",
                "encuestame.org.core.commons.tweetPoll.TweetPollList",
                "encuestame.org.core.commons.tweetPoll.TweetPollListDetail",
                "encuestame.org.core.commons.tweetPoll.HashTags",
                "encuestame.org.core.commons.tweetPoll.Answers",
                "encuestame.org.core.comments.Comment",
                "encuestame.org.core.commons.poll.Poll",
                "encuestame.org.core.commons.poll.PollNavigate",
                "encuestame.org.core.commons.dialog.Info",
                "encuestame.org.core.shared.utils.FolderSelect",
                "encuestame.org.core.commons.dashboard.GadgetDirectory",
                "encuestame.org.core.commons.social.SocialAccounts",
                "encuestame.org.core.commons.dashboard.Dashboard",
                "encuestame.org.core.commons.dashboard.DashboardLayout",
                "encuestame.org.core.commons.dashboard.DashboardWrapper",
                "encuestame.org.core.commons.dashboard.Gadget",
                "encuestame.org.core.shared.utils.FolderOperations",
                "encuestame.org.core.shared.utils.Avatar",
                "encuestame.org.core.commons.dashboard.chart.DashboardPie",
                "encuestame.org.core.commons.dashboard.chart.DashboardLine",
                "encuestame.org.core.commons.dashboard.chart.DashboardColumn2D",
                "encuestame.org.core.commons.notifications.Notification",
                "encuestame.org.core.commons.notifications.NotificationItem",
                "encuestame.org.core.commons.notifications.NotificationList",
                "encuestame.org.core.admon.project.Projects",
                "encuestame.org.core.admon.user.Users",
                "encuestame.org.core.commons.dashboard.chart.EncuestamePieChart",
                "encuestame.org.core.shared.utils.YesNoWidget",
                "encuestame.org.core.map.Map",
                "encuestame.org.core.commons.profile.ProfileMenu",
                "encuestame.org.core.commons.profile.Profile",
                "encuestame.org.core.commons.dashboard.DashBoardMenu",
                "encuestame.org.core.commons.security.Password",
                "encuestame.org.core.admon.location.Locations",
                "encuestame.org.core.commons.error.ErrorSessionHandler",
                "encuestame.org.core.commons.error.ErrorConexionHandler",
                "encuestame.org.core.commons.error.ErrorHandler",
                "encuestame.org.core.commons.dialog.Confirm",
                "encuestame.org.core.commons.dialog.Dialog",
                "encuestame.org.core.commons.signup.SignupProfile",
                "encuestame.org.core.commons.security.Login",
                "encuestame.org.core.shared.utils.Suggest",
                "encuestame.org.core.shared.utils.Table",
                "encuestame.org.core.commons.error.AbstractErrorHandler",
                "encuestame.org.core.commons",
                "encuestame.org.core.commons.search.SearchMenu",
                "encuestame.org.core.commons.search.SearchSuggestItem",
                "encuestame.org.activity.Activity",
                "encuestame.org.core.commons.hashtags.Cloud",
                "encuestame.org.core.commons.rated.Comments",
                "encuestame.org.core.commons.rated.Users",
                "encuestame.org.core.commons.stream.FrontEnd",
                "encuestame.org.core.commons.stream.HashTagInfo",
                "encuestame.org.core.commons.stream.FrontEndItem",
                "encuestame.org.core.commons.rated.HashTags",
                "encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail",
                "encuestame.org.core.commons.tweetPoll.detail.TweetPollInfoDetail",
                "encuestame.org.core.shared.utils.CountDown",
                "encuestame.org.core.shared.utils.SurveyStatus",
                "encuestame.org.core.commons.validator.AbstractValidatorWidget",
                "encuestame.org.core.commons.validator.PasswordValidator",
                "encuestame.org.core.commons.validator.EmailValidator",
                "encuestame.org.core.commons.validator.PasswordValidator",
                "encuestame.org.core.commons.validator.RealNameValidator",
                "encuestame.org.core.commons.validator.UsernameValidator",
                "encuestame.org.core.shared.utils.StandBy",
                "encuestame.org.core.commons.panel.PanelBar",
                "encuestame.org.core.shared.utils.FoldersActions",
                //mobile
                "encuestame.org.mobile.notifications.NotificationList",
                "encuestame.org.mobile.tweetPoll.TweetPollList",
                //support,
                "encuestame.org.core.commons.support.Wipe",
                "org.cometd",
                "org.cometd.AckExtension",
                "org.cometd.ReloadExtension",
                "org.cometd.TimeStampExtension",
                "org.cometd.TimeSyncExtension",
                //gadgets
                "encuestame.org.core.gadget.Gadget",
                "encuestame.org.core.gadget.Activity",
                "encuestame.org.core.gadget.Comments",
                "encuestame.org.core.gadget.TweetPollVotes",
                //patterns
                "encuestame.org.core.commons.questions.Question",
                "encuestame.org.core.commons.questions.patterns.SingleResponse",
                "encuestame.org.core.commons.questions.patterns.AbstractPattern",
                "encuestame.org.core.shared.publish.PublishSupport",
                "encuestame.org.core.shared.utils.ContextSupport",
                "encuestame.org.core.commons.support.DnD",
                "encuestame.org.core.shared.utils.Icon"
                ]
            }
          /*,
            {
                name: "../dijit/dijit.js",
                dependencies: [
                    "dijit.dijit"
                ]
            },
            {
                name: "../dijit/dijit-all.js",
                layerDependencies: [
                    "../dijit/dijit.js"
                ],
                dependencies: [
                    "dijit.dijit-all"
                ]
            }*/
        ],
        prefixes: [
            ["encuestame", "../../encuestame-war/src/main/webapp/resource/js/encuestame"],
            ["dijit", "../dijit" ],
            ["dojox", "../dojox" ],
            ["org", "../../encuestame-war/src/main/webapp/resource/js/org" ]
        ]
    };
