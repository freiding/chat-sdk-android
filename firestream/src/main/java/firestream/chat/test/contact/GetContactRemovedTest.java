package firestream.chat.test.contact;

import firestream.chat.events.EventType;
import firestream.chat.namespace.Fire;
import firestream.chat.test.Result;
import firestream.chat.test.Test;
import firestream.chat.test.TestScript;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class GetContactRemovedTest extends Test {

    public GetContactRemovedTest() {
        super("GetContactRemoved");
    }

    @Override
    public Observable<Result> run() {
        return Observable.create((ObservableOnSubscribe<Result>) emitter -> {
            manage(emitter);
            dm.add(Fire.stream().getContactEvents().currentAndNewEvents().subscribe(userEvent -> {
                if (userEvent.typeIs(EventType.Removed)) {
                    if (userEvent.get().equals(TestScript.testUser1())) {
                        complete();
                    } else {
                        failure("Wrong user removed");
                    }
                } else {
                    failure("No contact removed");
                }
                complete();
            }, this));
        }).subscribeOn(Schedulers.io());
    }

}
