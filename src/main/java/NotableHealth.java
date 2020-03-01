import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static spark.Spark.*;

public class NotableHealth {

    public static void main(String[] args) {
        RestLayer restLayer = new RestLayer();
        restLayer.serve();
    }


}
