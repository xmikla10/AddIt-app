package addit.vutbr.fit.addit.model;

import android.graphics.Color;

import addit.vutbr.fit.addit.R;

/**
 * Enum representing the priority of task green < orange < red
 */
public enum Priority {
    red {
        @Override
        public int getDrawable() {
            return R.drawable.circle_red;
        }
    },
    orange {
        @Override
        public int getDrawable() {
            return R.drawable.circle_orange;
        }
    },
    green {
        @Override
        public int getDrawable() {
            return R.drawable.circle_green;
        }
    };
    public abstract int getDrawable();
}
