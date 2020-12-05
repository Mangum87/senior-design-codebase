package com.fitbitsample.FitbitDataType.SleepData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Used to retrieve the sleep information from FitBit.
 */
public class Sleep
{
        @SerializedName("sleep")
        @Expose
        private List<SleepData> sleep = null;

        /*@SerializedName("summary")
        @Expose
        private SleepSummary summary;*/

        public List<SleepData> getSleep() {
            return sleep;
        }

        public void setSleep(List<SleepData> sleep) {
            this.sleep = sleep;
        }

        /*public SleepSummary getSummary() {
            return summary;
        }

        public void setSummary(SleepSummary summary) {
            this.summary = summary;
        }*/

        @Override
        public String toString()
        {
            /*return "sleep:[\n{\n" + sleep.get(0).toString() + "\n}," +
                    "\n],\nsummary: {\n" +
                    summary.toString() + "\n}\n}";*/

            return "sleep:[\n{\n" + sleep.get(0).toString() + "\n}]";
        }
}
