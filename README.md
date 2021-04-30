# IF3210-2021-Android-K3-07
Tugas Besar 3 IF3210 - Android Workout

## Deskripsi Aplikasi
Aplikasi workout merupakan aplikasi pada platform Android yang menunjanng kegiatan workout. Fitur-fitur yang disediakan adalah berita olahraga, pelacakan latihan untuk jenis latihan walking (berjalan) dan cycling (bersepeda), pencatatan history latihan, dan penjadwalan latihan. 

## Cara Kerja
### Sport News <br/>
Ketika fragment untuk sport news aktif, aplikasi akan <b>mengambil data dari API</b> sport news menggunakan retrofit dan mengubahnya ke dalam bentuk list class News yang terdiri dari atribut judul berita, deskripsi berita, url gambar, dan url berita. Lalu dengan menggunakan <b>recycler view</b> dan mengattach adaptor untuk list berita sehingga setiap item class pada list akan ditampilkan dalam satu card yang terdiri dari gambar, judul, dan deskripsi berita. 

Design halaman juga <b>responsive</b>. Jika orientasi device potrait, card akan ditampilkan dalam satu kolom, sedangkan jika orientasinya landscape card ditampilkan dalam dua kolom. Ketika salah satu card diklik, maka akan dibuat intent baru yang memuat <b>webview</b> untuk menampilakn berita sesuai url item tersebut.

### Training Tracker
Training tracker merupakan halaman yang digunakan untuk melakukan pelacakan latihan yang terdiri dari dua jenis, yaitu <b>Cycling</b> yang melacak jarak tempuh dan rute yang dilalui berupa angka dan peta serta <b>Walking/Running</b> yang melacak jumlah step yang dilakukan. Sistem pelacakan ini berjalan pada <b>background</b> memanfaatkan services dan memiliki <b>kompas</b> yang diimplementasikan dengan memanfaatkan sensor. Ketika button start ditekan, timer dan sistem pelacakan akan mulai berjalan pada background dan mencatat jumlah langkah atau kilometer dan lokasi pengguna. Setelah button stop ditekan, history tersebut akan disimpan ke database dan akan dibuat intent baru yang menampilkan history exercise yang baru dilakukan.

### Training History
Training history ditampilkan dengan memanfaatkan recycler view dan card untuk setiap item pada database history yang disimpan menggunakan SQLite. Pengambilan data (query) dilakukan menggunakan DAO sesuai tanggal yang dipilih pada kalender. Log history dan detail log history ini diimplementasikan menggukanan <b>fragment</b> yang mendukung <b>responsive</b>. 

Ketika layar dalam keadaan potrait, framelayout yang digunakan hanya satu untuk history log saja dan ketika item diklik akan dilakukan replace fragment history log menjadi history log details. Ketika device dalam keadaan landscape, digunakan dua framelayout masing-masing satu untuk history log dan satu untuk history log details bersebelahan. 

Untuk history cycling, terdapat button untuk melihat maps rute pelacakan lokasi selama pengguna bersepeda. Maps dimuat dalam sebuah intent dan garis rute digambarkan dengan <b>polylines</b> di atas google maps dari pemanggilan menggunakan google maps API.

### Training Scheduler

## Library yang Digunakan
* Retrofit : digunakan untuk mengambil data berita olahraga dari API. Justifikasi penggunaannya adalah karena library ini dapat mengubah antarmuka API menjadi callable object sesuai dengan class yang kita buat, sehingga untuk menampilkan hasil API call tersebut dapat mengakses atribut kelas dari objek tersebut.

* EasyPermissions : digunakan untuk meminta izin akses terhadap perangkat. Justifikasi penggunaannya adalah karena library ini dapat menyederhanakan sistem logika perihal permintaan izin akses ketika menghadapi sistem operasi Android M ke atas sehingga mengurus izin akses menjadi lebih mudah.

* Timber : digunakan untuk mencetak data dari kode ke terminal. Justifikasi penggunaannya adalah karena library ini dapat mempermudah dalam debugging kode untuk memeriksa apakah aplikasi yang dibuat sudah sesuai atau belum langsung pada Build, tidak pada Debug sehingga proses pemeriksaan hasil kode lebih mudah dan cepat.

* Google Play Service Maps dan Location : digunakan untuk menggunakan google maps pada aplikasi. Justifikasi penggunaannya adalah karena dengan library ini kita dapat memanfaatkan dan memanggil API google maps sehingga bisa digunakan dalam aplikasi.

* Glide : digunakan untuk menampilkan gambar pada card dari sumber url. Justifikasi penggunannya karena penggunaaanya sederhana dan dapat menampilkan gambar yang bersumber dari url

## Screenshot Aplikasi
### Tampilan potrait Sport News<br/>
<img src = "screenshot/sportnews.png" width="300px"/><br/>
### Tampilan landscape Sport News<br/>
<img src = "screenshot/sportnews2.png" width="600px"/><br/>
### Tampilan Webview Sport News<br/>
<img src = "screenshot/sportnews3.png" width="300px"/><br/>
### Tracker<br/>
<img src = "screenshot/tracker.png" width="300px"/><br/>
### Tracker start<br />
<img src = "screenshot/tracker2.png" width="300px"/><br/>
### Training History<br/>
<img src = "screenshot/history.png" width="300px"/><br/>
### Log History potrait<br/>
<img src = "screenshot/history2.png" width="300px"/><br/>
### Detail log History potrait Walking<br/>
<img src = "screenshot/history3.jpg" width="300px"/><br/>
### Log dan detail History landscape<br/>
<img src = "screenshot/history4.png" width="600px"/><br/>
### Detail log History potrait Cycling<br/>
<img src = "screenshot/history5.png" width="300px"/><br/>
### Map rute Cycling<br/>
<img src = "screenshot/history6.png" width="300px"/><br/>
### Scheduler<br/>
<img src = "screenshot/schedule.png" width="300px"/><br/>
### Add new Schedule
<img src = "screenshot/schedule2.png" width="300px"/><br/>
### Notification
<img src = "screenshot/schedule3.png" width="300px"/><br/>

## Pembagian Kerja Anggota Kelompok
| NIM | Nama | Pembagian Kerja |
|-----|------|-----------------|
| 13518045 | Anna Elvira Hartoyo | Fitur Sport News<br/>Fitur Training History |
| 13518096 | Naufal Arfananda Ghifari | Fitur Training Scheduler|
| 13518126 | Evan Pradanika | Fitur Training Tracker|
