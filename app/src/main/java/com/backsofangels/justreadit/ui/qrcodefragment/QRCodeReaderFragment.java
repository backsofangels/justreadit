package com.backsofangels.justreadit.ui.qrcodefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.backsofangels.justreadit.R;
import com.backsofangels.justreadit.model.ScannedLink;
import com.backsofangels.justreadit.persistence.ScannedLinkDao;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.journeyapps.barcodescanner.ViewfinderView;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class QRCodeReaderFragment extends Fragment {
    private ViewPager mainActivityViewPager;
    private BarcodeView barcodeView;
    private ViewfinderView viewfinderView;
    private String scannedText;
    private ScannedLinkDao dao;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if(result.getText() == null || result.getText().equals(scannedText)) {
                return;
            }
            scannedText = result.getText();
            System.out.println("Found QR. URI is " + scannedText);
            ScannedLink l = new ScannedLink(scannedText, new Date());
            dao.saveLink(l);
            Snackbar scanDoneNotification = Snackbar.make(getView(), "Link scannerizzato!", Snackbar.LENGTH_LONG);
            scanDoneNotification.setAction("Vedi", new ChangePageListener());
            scanDoneNotification.show();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    public class ChangePageListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mainActivityViewPager.setCurrentItem(1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.qrcodefragment_layout, parent, false);
        barcodeView = v.findViewById(R.id.qrfragment_barcodeview);
        viewfinderView = v.findViewById(R.id.qrfragment_viewfinder);
        viewfinderView.setCameraPreview(barcodeView);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivityViewPager = getActivity().findViewById(R.id.main_viewpager);
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.AZTEC, BarcodeFormat.MAXICODE, BarcodeFormat.QR_CODE);
        barcodeView.setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeView.decodeContinuous(callback);
        dao = ScannedLinkDao.getInstance();
    }

    @Override
    public void onResume() {
        barcodeView.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        barcodeView.pause();
        super.onPause();
    }
}
