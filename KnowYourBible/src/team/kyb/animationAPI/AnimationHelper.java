package team.kyb.animationAPI;

import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

public class AnimationHelper {
	
	public AnimationHelper() {
		
	}
	
    public boolean applyRotation(ImageView image1, ImageView image2,
			float start, float end) {
		// Find the center of image
		final float centerX = image1.getWidth() / 2.0f;
		final float centerY = image1.getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		final Flip3dAnimation rotation = new Flip3dAnimation(start, end,
				centerX, centerY);
		rotation.setDuration(500);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());

		rotation.setAnimationListener(new DisplayNextView(image1, image2));
		image1.startAnimation(rotation);

		return true;

	}
}
