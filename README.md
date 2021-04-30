# IF3210-2021-Android-K3-07
Tugas Besar 3 IF3210 - Android Workout

## Deskripsi Aplikasi
Aplikasi workout merupakan aplikasi pada platform Android yang menunjanng kegiatan workout. Fitur-fitur yang disediakan adalah berita olahraga, pelacakan latihan untuk jenis latihan walking (berjalan) dan cycling (bersepeda), pencatatan history latihan, dan penjadwalan latihan. 

## Cara Kerja
### Sport News <br/>
Ketika fragment untuk sport news aktif, aplikasi akan <b>mengambil data dari API</b> sport news menggunakan retrofit dan mengubahnya ke dalam bentuk list class News yang terdiri dari atribut judul berita, deskripsi berita, url gambar, dan url berita. Lalu dengan menggunakan <b>recycler view</b> dan mengattach adaptor untuk list berita sehingga setiap item class pada list akan ditampilkan dalam satu card yang terdiri dari gambar, judul, dan deskripsi berita. 

<br/>Design halaman juga <b>responsive</b>. Jika orientasi device potrait, card akan ditampilkan dalam satu kolom, sedangkan jika orientasinya landscape card ditampilkan dalam dua kolom. Ketika salah satu card diklik, maka akan dibuat intent baru yang memuat <b>webview</b> untuk menampilakn berita sesuai url item tersebut.

### Training Tracker

### Training History
Training history ditampilkan dengan memanfaatkan recycler view dan card untuk setiap item pada database history yang disimpan menggunakan SQLite. Pengambilan data (query) dilakukan menggunakan DAO sesuai tanggal yang dipilih pada kalender. Log history dan detail log history ini diimplementasikan menggukanan <b>fragment</b> yang mendukung <b>responsive</b>. 
<br/>Ketika layar dalam keadaan potrait, framelayout yang digunakan hanya satu untuk history log saja dan ketika item diklik akan dilakukan replace fragment history log menjadi history log details. Ketika device dalam keadaan landscape, digunakan dua framelayout masing-masing satu untuk history log dan satu untuk history log details bersebelahan

### Training Scheduler

## Library yang Digunakan
* Retrofit : digunakan untuk mengambil data berita olahraga dari API. Justifikasi penggunaannya adalah karena library ini dapat mengubah antarmuka API menjadi callable object sesuai dengan class yang kita buat, sehingga untuk menampilkan hasil API call tersebut dapat mengakses atribut kelas dari objek tersebut.

## Screenshot Aplikasi
* Tampilan potrait Sport News
* Tampilan landscape Sport News
* Tampilan Webview Sport News
* Tracker
* Training History
* Log History potrait
* Detail log History potrait
* Log dan detail History landscape
* Scheduler

## Pembagian Kerja Anggota Kelompok
| NIM | Nama | Pembagian Kerja |
|-----|------|-----------------|
| 13518045 | Anna Elvira Hartoyo | Fitur Sport News<br/>Fitur Training History |
| 13518096 | Naufal Arfananda Ghifari | Fitur Training Scheduler|
| 13518126 | Evan Pradanika | Fitur Trainig Tracker|
