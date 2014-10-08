<h1> Week 4 Project: Twitter with Fragments</h1>

<h3>Time Spent: 20 hours </h3>

<p><strong>User Stories</strong>:</p>

<p>The following user stories <strong>must</strong> be completed:</p>

<ul>
<li>[x]Includes <strong>all required user stories</strong> from Week 3 Twitter Client</li>
<li>[x]User can switch between Timeline and Mention views using tabs.

<ul>
<li>[x]User can view their home timeline tweets.</li>
<li>[x]User can view the recent mentions of their username.</li>
<li>[x]User can scroll to bottom of either of these lists and new tweets will load ("infinite scroll")</li>
<li><strong>Optional:</strong> Implement tabs in a <a href="http://guides.codepath.com/android/ActionBar-Tabs-with-Fragments#with-actionbaractivity-support">gingerbread-compatible approach</a></li>
</ul></li>
<li>[x]User can navigate to <strong>view their own profile</strong>

<ul>
<li>[x]User can see picture, tagline, # of followers, # of following, and tweets on their profile.</li>
</ul></li>
<li>[x]User can <strong>click on the profile image</strong> in any tweet to see <strong>another user's</strong> profile.

<ul>
<li>[x]User can see picture, tagline, # of followers, # of following, and tweets of clicked user.</li>
<li>[x]Profile view should include that user's timeline</li>
<li><strong>Optional:</strong> User can view following / followers list through the profile</li>
</ul></li>
</ul>

<p>The following advanced user stories are <strong>optional</strong>:</p>

<ul>
<li><strong>[x]Advanced:</strong> Robust error handling, <a href="http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity">check if internet is available</a>, handle error cases, network failures</li>
<li><strong>[x]Advanced:</strong> When a network request is sent, user sees an <a href="http://guides.codepath.com/android/Handling-ProgressBars#actionbar-progress-bar">indeterminate progress indicator</a></li>
<li><strong>[x]Advanced:</strong> User can "reply" to any tweet on their home timeline

<ul>
<li>[x}The user that wrote the original tweet is automatically "@" replied in compose</li>
</ul></li>
<li><strong>[x]Advanced:</strong> User can click on a tweet to be taken to a "detail view" of that tweet

<ul>
<li><strong>[X]Advanced:</strong> User can take favorite (and unfavorite) or reweet actions on a tweet</li>
</ul></li>
<li><strong>[x]Advanced:</strong> Improve the user interface and theme the app to feel twitter branded</li>
<li><strong>[x]Advanced</strong>: User can search for tweets matching a particular query and see results</li>

<li><strong>Bonus:</strong> User can view their direct messages (or send new ones)</li>
</ul>

![Alt text](https://github.com/cassiomo/MyTwitter/blob/master/reply.gif "reply.gif")

![Alt text](https://github.com/cassiomo/MyTwitter/blob/master/layout.gif "layout.gif")

![Alt text](https://github.com/cassiomo/MyTwitter/blob/master/detailview.gif "detailview.gif")

![Alt text](https://github.com/cassiomo/MyTwitter/blob/master/AdvanceTwitter.gif "AdvanceTwitter.gif")

![Alt text](https://github.com/cassiomo/MyTwitter/blob/master/SearchTwitter.gif "SearchTwitter.gif.gif")

<h1> Week 3 Project: Simple Twitter Client</h1>

Build a simple Twitter client that supports viewing a Twitter timeline and composing a new tweet.

<h3>Time Spent: 25 hours </h3>

<h3>Completed users stories:<h3>

[x] Required: User can sign in to Twitter using OAuth login<br>
[x] Required: User can view the tweets from their home timeline<br>
&nbsp;&nbsp;&nbsp;&nbsp;User should be displayed the username, name, and body for each tweet<br>
&nbsp;&nbsp;&nbsp;&nbsp;User should be displayed the relative timestamp for each tweet "8m", "7h"<br>
&nbsp;&nbsp;&nbsp;&nbsp;User can view more tweets as they scroll with infinite pagination<br>
&nbsp;&nbsp;&nbsp;&nbsp;Optional: Links in tweets are clickable and will launch the web browser (see autolink)<br>
[x] Required: User can compose a new tweet<br>
&nbsp;&nbsp;&nbsp;&nbsp;User can click a “Compose” icon in the Action Bar on the top right<br>
&nbsp;&nbsp;&nbsp;&nbsp;User can then enter a new tweet and post this to twitter<br>
&nbsp;&nbsp;&nbsp;&nbsp;User is taken back to home timeline with new tweet visible in timeline<br>
&nbsp;&nbsp;&nbsp;&nbsp;Optional: User can see a counter with total number of characters left for tweet<br>
[x] Advanced: User can refresh tweets timeline by pulling down to refresh (i.e pull-to-refresh)<br>
[x] Advanced: User can open the twitter app offline and see last loaded tweets<br>
&nbsp;&nbsp;&nbsp;&nbsp;Tweets are persisted into sqlite and can be displayed from the local DB<br>
[] Advanced: User can tap a tweet to display a "detailed" view of that tweet<br>
[] Advanced: User can select "reply" from detail view to respond to a tweet<br>
[] Advanced: Improve the user interface and theme the app to feel "twitter branded"<br>
[] Bonus: User can see embedded image media within the tweet detail view<br>
[] Bonus: Compose activity is replaced with a modal overlay<br>

![Alt text](https://github.com/cassiomo/MyTwitter/blob/master/simpletwitter.gif "simpletwitter.gif")

![Alt text](https://github.com/cassiomo/MyTwitter/blob/master/simpletwitter.gif "simpletwitter.gif")

![Alt text](https://github.com/cassiomo/MyTwitter/blob/master/simpletwitterpullrefresh.gif "simpletwitterpullrefresh.gif")

![Alt text](https://github.com/cassiomo/MyTwitter/blob/master/table.gif "table.gif")

![Alt text](https://github.com/cassiomo/MyTwitter/blob/master/link.gif "link.gif")

![Alt text](https://github.com/cassiomo/MyTwitter/blob/master/PostTweet.gif "PostTweet.gif")