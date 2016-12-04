package com.quran.labs.androidquran.presenter.bookmark;

import com.quran.labs.androidquran.data.Constants;
import com.quran.labs.androidquran.model.bookmark.RecentPageModel;
import com.quran.labs.androidquran.presenter.Presenter;
import com.quran.labs.androidquran.ui.PagerActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecentPagePresenter implements Presenter<PagerActivity> {
  private final RecentPageModel model;

  private int lastPage;
  private int minimumPage;
  private int maximumPage;

  @Inject
  RecentPagePresenter(RecentPageModel model) {
    this.model = model;
  }

  public void onPageChanged(int page) {
    model.updateLatestPage(page);

    lastPage = page;
    if (minimumPage == Constants.NO_PAGE) {
      minimumPage = page;
      maximumPage = page;
    } else if (page < minimumPage) {
      minimumPage = page;
    } else if (page > maximumPage) {
      maximumPage = page;
    }
  }

  @Override
  public void bind(PagerActivity what) {
    minimumPage = Constants.NO_PAGE;
    maximumPage = Constants.NO_PAGE;
    lastPage = Constants.NO_PAGE;
  }

  @Override
  public void unbind(PagerActivity what) {
    if (minimumPage != Constants.NO_PAGE || maximumPage != Constants.NO_PAGE) {
      model.persistLatestPage(minimumPage, maximumPage, lastPage);

      minimumPage = Constants.NO_PAGE;
      maximumPage = Constants.NO_PAGE;
    }
    lastPage = Constants.NO_PAGE;
  }
}