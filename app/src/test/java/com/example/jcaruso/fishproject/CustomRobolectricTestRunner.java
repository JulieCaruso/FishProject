package com.example.jcaruso.fishproject;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

import java.lang.reflect.Method;

public class CustomRobolectricTestRunner extends RobolectricTestRunner {

    private static final int SDK_EMULATE_LEVEL = 21;

    /**
     * Creates a runner to run {@code testClass}. Looks in your working directory for your AndroidManifest.xml file
     * and res directory by default. Use the {@link Config} annotation to configure.
     *
     * @param testClass the test class to be run
     * @throws InitializationError if junit says so
     */
    public CustomRobolectricTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        String buildVariant = (BuildConfig.FLAVOR.isEmpty() ? "" : BuildConfig.FLAVOR + "/") + BuildConfig.BUILD_TYPE;
        System.setProperty("android.package", BuildConfig.APPLICATION_ID);
        System.setProperty("android.manifest", "../../../app/build/intermediates/manifests/full/" + buildVariant + "/AndroidManifest.xml");
        System.setProperty("android.resources", "../../../app/build/intermediates/res/" + buildVariant);
        System.setProperty("android.assets", "../../../app/build/intermediates/assets/" + buildVariant);
    }

    @Override
    public Config getConfig(Method method) {
        final Config defaultConfig = super.getConfig(method);
        return new Config.Implementation(
                defaultConfig.sdk(),
                defaultConfig.minSdk(),
                defaultConfig.maxSdk(),
                defaultConfig.manifest(),
                defaultConfig.qualifiers(),
                defaultConfig.packageName(),
                defaultConfig.abiSplit(),
                defaultConfig.resourceDir(),
                defaultConfig.assetDir(),
                defaultConfig.buildDir(),
                defaultConfig.shadows(),
                defaultConfig.instrumentedPackages(),
                TestApp.class,
                defaultConfig.libraries(),
                defaultConfig.constants() == Void.class ? BuildConfig.class : defaultConfig.constants()
        );
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        //String appRoot = "../../../app/build/intermediates/";
        String appRoot = "C:/Users/jcaruso/AndroidStudioProjects/FishProject/app/build/intermediates/";
        String manifestPath = appRoot + "manifests/full/debug/AndroidManifest.xml";
        String resDir = appRoot + "res/merged/debug";
        String assetsDir = appRoot + "assets";
        AndroidManifest manifest = new AndroidManifest(Fs.fileFromPath(manifestPath),
                Fs.fileFromPath(resDir),
                Fs.fileFromPath(assetsDir));

        manifest.setPackageName("com.example.jcaruso.fishproject");
        // Robolectric is already going to look in the  'app' dir ...
        // so no need to add to package name
        return manifest;
    }


}
