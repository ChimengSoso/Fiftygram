<main class="col-md markdown-body">

<h1 id="fiftygram">Fiftygram</h1>

<h2 id="distribution-code">Distribution Code</h2>

<p>Download this project’s <a href="https://cdn.cs50.net/2019/fall/tracks/android/fiftygram/fiftygram.zip">distribution code</a>.</p>

<p>To open the distribution code, extract the ZIP, open Android Studio, select “Import project”, and select the folder you extracted from the ZIP.</p>

<h2 id="what-to-do">What To Do</h2>

<ul>
  <li data-marker="*">More Filters</li>
  <li data-marker="*">Saving Photos</li>
</ul>

<h2 id="more-filters">More Filters</h2>

<p>We’ve added a few different filters together, but now try experimenting with your own! Add at least one new filter of your choosing to the app. Be creative!</p>

<h2 id="saving-photos">Saving Photos</h2>

<p>Our app can apply filters to photos, but it would be nice if we could save those photos so we could post them elsewhere!</p>

<p>First, some bookkeeping. Android has a pretty strict permissions model, so your app will need to request permission to store a photo to the user’s device. Different versions of Android handle these permissions differently, so for simplicity’s sake, make sure your app has a minimum SDK version of 23. To set the minimum SDK version, open up <code class="highlighter-rouge">build.gradle</code>, and make sure you have:</p>

<div class="highlighter-rouge"><div class="highlight"><pre class="highlight"><code>minSdkVersion 23
</code></pre></div></div>

<p>If you don’t, just change the number next to <code class="highlighter-rouge">minSdkVersion</code>, and then click <code class="highlighter-rouge">Sync now</code>!</p>

<p>Next, open up <code class="highlighter-rouge">AndroidManifest.xml</code> and add a line right above <code class="highlighter-rouge">&lt;/manifest&gt;</code>:</p>

<div class="highlighter-rouge"><div class="highlight"><pre class="highlight"><code>&lt;uses-permission
    android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    tools:remove="android:maxSdkVersion" /&gt;
</code></pre></div></div>

<p>This element tells Android that our app will need permission to write to external storage.</p>

<p>Finally, we need to actually request permission from the app. For this, we’ll implement an interface called <code class="highlighter-rouge">ActivityCompat.OnRequestPermissionsResultCallback</code> like this:</p>

<div class="highlighter-rouge"><div class="highlight"><pre class="highlight"><code>public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
</code></pre></div></div>

<p>Then, we can request permissions when the app loads by adding the following to <code class="highlighter-rouge">onCreate</code>:</p>

<div class="highlighter-rouge"><div class="highlight"><pre class="highlight"><code>requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
</code></pre></div></div>

<p>This should pop-up a dialog that allows the user to allow or deny the permission. You can check the result of that dialog by adding the below method:</p>

<div class="highlighter-rouge"><div class="highlight"><pre class="highlight"><code>@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
}
</code></pre></div></div>

<p>That’s it for bookkeeping, so let’s implement our save functionality now! Add a new <code class="highlighter-rouge">Button</code> to the layout, and use <code class="highlighter-rouge">android:onClick</code> to wire it up to a method in your <code class="highlighter-rouge">MainActivity</code>. Inside of that method, you’ll want to get a <code class="highlighter-rouge">Bitmap</code> of the modified image, and then use <code class="highlighter-rouge">MediaStore.Images.Media.insertImage</code> to save the file.</p>

<p>To test, you can open up the <code class="highlighter-rouge">Photos</code> app in the emulator, and you should see filtered photos saved there.</p>

<h2 id="how-to-submit">How to Submit</h2>

<p>To submit your code with <code class="highlighter-rouge">submit50</code>, you may either: (1) upload your code to CS50 IDE and run <code class="highlighter-rouge">submit50</code> from inside of your IDE, or (2) install <code class="highlighter-rouge">submit50</code> on your own computer by running <code class="highlighter-rouge">pip3 install submit50</code> (assuming you have <a href="https://www.python.org/downloads/">Python 3</a> installed).</p>

<p>Execute the below, logging in with your GitHub username and password when prompted. For security, you’ll see asterisks (<code class="highlighter-rouge">*</code>) instead of the actual characters in your password.</p>

<div class="highlighter-rouge"><div class="highlight"><pre class="highlight"><code>submit50 cs50/problems/2020/x/tracks/android/fiftygram
</code></pre></div></div>


</main>
