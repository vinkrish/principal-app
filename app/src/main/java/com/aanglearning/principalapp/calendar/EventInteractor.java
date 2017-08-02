package com.aanglearning.principalapp.calendar;

import com.aanglearning.principalapp.model.Evnt;

import java.util.List;

/**
 * Created by Vinay on 31-07-2017.
 */

interface EventInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onEventsReceived(List<Evnt> eventsList);
    }

    void getEvents(long schoolId, EventInteractor.OnFinishedListener listener);
}
