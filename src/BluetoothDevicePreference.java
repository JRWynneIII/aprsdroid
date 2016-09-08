	package de.duenndns;

	import android.bluetooth.*;
	import android.content.Context;
	import android.preference.ListPreference;
	import android.util.AttributeSet;

	import java.util.Set;

	public class BluetoothDevicePreference extends ListPreference {

		public BluetoothDevicePreference(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		protected void onPrepareDialogBuilder(android.app.AlertDialog.Builder builder) {
			// hook into the builder to refresh the list
			BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
			Set<BluetoothDevice> pairedDevices = (bta != null) ? bta.getBondedDevices() : null;
			if (pairedDevices == null) {
				super.onPrepareDialogBuilder(builder);
				return;
			}

			CharSequence[] entries = new CharSequence[pairedDevices.size()];
			CharSequence[] entryValues = new CharSequence[pairedDevices.size()];
			int i = 0;
			for (BluetoothDevice dev : pairedDevices) {
				if (dev.getAddress() != null) {
					entries[i] = dev.getName();
					if (entries[i] == null)
						entries[i] = "(null)";
					entryValues[i] = dev.getAddress();
					i++;
				}
			}
			setEntries(entries);
			setEntryValues(entryValues);

			super.onPrepareDialogBuilder(builder);
		}

		public BluetoothDevicePreference(Context context) {
			this(context, null);
		}

	}
