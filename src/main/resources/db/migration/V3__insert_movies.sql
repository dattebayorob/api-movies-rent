UPDATE public.tb_movie SET picture_path = 'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQJmbajoepvv5M8KO0eev1s80qkpthWlbLvWR1L0BsMat307ypG'
  WHERE id = 1;
  
INSERT INTO tb_people ( id, "name" ) VALUES
(5, 'Samuel L. Jackson'),
(6, 'Robert Downey Jr'),
(7, 'Chris Evans'),
(8, 'Chris Hemsworth'),
(9, 'Scarlett Johansson'),
(10, 'Gwyneth Paltrow'),
(11, 'Don Cheadle'),
(12, 'Tom Hiddleston'),
(13, 'Mark Ruffalo'),
(14, 'Cobie Smulders'),
(15, 'Benedict Cumberbatch'),
(16, 'Brothers Russo'),
(17, 'Ana Boden'),
(18, 'Taika Waititi'),
(19, 'Peyton Reed'),
(20, 'Ryan Coogler'),
(21, 'Chadwick Boseman'),
(22, 'Michael B. Jordan'),
(23, 'Lupita Nyong o'),
(24, 'Jon Watts');

INSERT INTO tb_movie ( name, quantity, director_id , screenwriter_id, picture_path )
VALUES ( 'Avengers: Endgame', 10, 16,16, 'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQA_-tL18_rj9zEcjN6n41NEaJm-kRNF9UeOtvksZ4z_OW6jRA9'),
('Captain Marvel', 5, 17,17, 'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQ1bDkDLq-_bteASakhnC1XYwlkErFuqcof7KMhFpRwVhCTh1Vo'),
('Avengers: Infinity War', 10,16,16, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmE2vwxy5KaCu7cRuYYdgNdQKddux5OYgGwsPo0kqP_xzLnsDV'),
('Thor: Ragnarok', 5, 18,18, 'http://www.movienewsletters.net/photos/256906R1.jpg'),
('Ant-Man and the Wasp', 5, 19,19, 'https://contentserver.com.au/assets/643721_g4.jpg'),
('Black Panther', 5, 20,20, 'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQPpcKQ9eWZGxJe6eXyCW91eayLVm4KqruvJz3GP0F2T2w46yKZ'),
('Spider-Man: Homecoming', 5, 24,24, 'http://www.movienewsletters.net/photos/252069R1.jpg');

INSERT INTO tb_movie_category (movie_id , category_id )
  VALUES ( 2, 1 ), (2,3), (3,1), (3,3), (4,1), (4,3), (5,1), (5,3), (6,1), (6,3), (7,1), (7,3);