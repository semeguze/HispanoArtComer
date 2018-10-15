package co.edu.javeriana.sebastianmesa.hispanoartcomer.decorators;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;

/**
 * Use a custom selector
 */
public class MySelectorDecorator implements DayViewDecorator {

  private final Drawable drawable;

  public MySelectorDecorator(Activity context) {
    drawable = context.getResources().getDrawable(R.drawable.my_selector);
    //drawable = getApplicationContext().getResources().getDrawable(R.drawable.ic_exit);
  }

  @Override
  public boolean shouldDecorate(CalendarDay day) {
    return true;
  }

  @Override
  public void decorate(DayViewFacade view) {
    view.setSelectionDrawable(drawable);
  }
}
